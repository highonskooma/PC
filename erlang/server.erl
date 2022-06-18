-module(server).
-export([start/1, stop/1, random_cristal/0]).

start(Port) -> spawn(fun() -> server_start(Port) end).
stop(Server) -> Server ! stop.

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

sendMessage(Pid, Message) -> Pid ! {broadcast, Message}.

room(Pids) ->
    receive
        stop -> io:format("room OK~n", []);
        {enter, Pid} ->
            io:format("user entered ~n", []),
            room([Pid | Pids]);
        {line, Data, Pid} ->
            io:format("received ~p ~n", [Data]),
            [ UPid ! {line,Data} || UPid <- Pids, UPid /= Pid ],
            
            Cristal=random_cristal(),
            X= io_lib:format("~.2f", [element(1, Cristal)]),
            Y= io_lib:format("~.2f", [element(2, Cristal)]),
            StringCristal="Cristal "++ X ++ ", " ++ Y++"\n",
            [sendMessage(UPid,StringCristal) || UPid <- Pids],
            io:format("received ~p ~n", [StringCristal]),

            room(Pids);
        {leave, Pid} ->
            io:format("user left ~n", []),
            room(Pids -- [Pid])
    end.

user(Sock, Room) ->
    receive
        {broadcast, Data} -> 
            gen_tcp:send(Sock,Data),
            user(Sock,Room);
        stop ->
            gen_tcp:send(Sock, "close"),
            io:format("user OK~n", []);
        {tcp, Socket, <<"quit", _/binary>>} ->
            gen_tcp:close(Socket),
            io:format("user left ~n");
        {line, Data} -> 
            gen_tcp:send(Sock, Data),
            %io:format("socket id ~p ~n", [Sock]),
            user(Sock, Room);
        {tcp, _, Data} ->
            Room ! {line, Data, self()},
            user(Sock, Room);
        {tcp_closed, _} ->
            Room ! {leave, self()};
        {tcp_error, _, _} ->
            Room ! {leave, self()}
    end.