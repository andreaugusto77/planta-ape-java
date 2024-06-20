import socket

host = "127.0.0.1"
port = 50000

with socket.socket(socket.AF_INET, socket.SOCK_STREAM) as s:
    s.bind((host, port))
    s.listen()
    print(f'Servidor na escuta em {host}:{port}')
    conn, addr = s.accept()
    with conn:
        print(f'Conectado em {addr}')
        while True:
            data = conn.recv(1024)
            if not data:
                break
            print(f'Mensagem recebida: {data.decode()}')
            conn.sendall(data)