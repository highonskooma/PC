-module(server).
-export([start/1, stop/1, random_cristal/0]).

start(Port) -> spawn(fun() -> server_start(Port) end).
stop(Server) -> Server ! stop.

%função para randomizar as coordenadas dos cristais
random_cristal() -> {rand:uniform()*rand:uniform(600),rand:uniform()*rand:uniform(600)}.

server_start(Port) ->
    {ok, LSock} = gen_tcp:listen(Port, [binary, {packet, line}, {reuseaddr, true}]),
    Room = spawn(fun()->room([]) end),
    FirstAcceptor = spawn(fun() -> acceptor(LSock, Room, self()) end),
    handleServer([FirstAcceptor], Room, LSock).

handleServer(List, Room, LSock) ->
    receive
        {new, Acceptor} -> handleServer([Acceptor | List], Room, LSock);
        stop ->
            [Acceptor ! stop || Acceptor <- List],
            Room ! stop,
            gen_tcp:shutdown(LSock, read_write)
    end.

acceptor(LSock, Room, Server) ->
    {ok, Sock} = gen_tcp:accept(LSock),
    NewAcceptor = spawn(fun() -> acceptor(LSock, Room, Server) end),
    Server ! {new, NewAcceptor},
    Room ! {enter, self()},
    user(Sock, Room).

room(Pids) ->
    receive
        stop -> io:format("room OK~n", []);
        {enter, Pid} ->
            % Verifica se existem mais de 2 players para começar o jogo, caso existam envia a mensagem start (num de players)
            Length = length(Pids) + 1,
            if
                Length>2 ->
                    Data = "start " ++ integer_to_list(Length) ++ "\n",
                    io:format("user entered -starting game ~n", []),
                    [ UPid ! {start,Data} || UPid <- Pids ],
                    Pid ! {start, Data};

                true ->
                    io:format("user entered ~n", []) 
            end,
            room([Pid | Pids]);

        {line, Data, Pid} ->
            % envia a mensagem com as suas coordenadas
            io:format("received ~p ~n", [Data]),
            [ UPid ! {line,Data} || UPid <- Pids, UPid /= Pid ],
            
            Cristal=random_cristal(),
            X= io_lib:format("~.2f", [element(1, Cristal)]),
            Y= io_lib:format("~.2f", [element(2, Cristal)]),
            C= integer_to_list(length(Pids)),
            StringCristal="Cristal "++ X ++ " " ++ Y++" "++"\n",
            %[sendMessage(UPid,StringCristal) || UPid <- Pids],
            %io:format("received ~p ~n", [StringCristal]),

            room(Pids);
        {leave, Pid} ->
            % envia a mensagem para notificar que o utilizador saiu
            io:format("user left ~n", []),
            room(Pids -- [Pid])
    end.

user(Sock, Room) ->
    receive
        % Envia a mensagem para começar o jogo para todos os clientes, assim como o número de jogadores
        {start, Data} -> 
            gen_tcp:send(Sock,Data),
            io:format("starting game~n",[]),
            user(Sock,Room);
        stop ->
            gen_tcp:send(Sock, "close"),
            io:format("user OK~n", []);
        {tcp, Socket, <<"quit", _/binary>>} ->
            gen_tcp:close(Socket),
            io:format("user left ~n");
        {line, Data} -> 
            gen_tcp:send(Sock, Data),
            user(Sock, Room);
        {tcp, _, Data} ->
            Room ! {line, Data, self()},
            user(Sock, Room);
        {tcp_closed, _} ->
            Room ! {leave, self()};
        {tcp_error, _, _} ->
            Room ! {leave, self()}
    end.
