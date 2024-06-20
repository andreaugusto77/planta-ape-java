import socket

host = "127.0.0.1"
port = 50000

with socket.socket(socket.AF_INET, socket.SOCK_STREAM) as s:
    s.connect((host, port))
    while True:
        message = input("Digite a mensagem que deseja enviar: ")
        if message.lower() == "Sair":
            break
        s.sendall(message.encode())
        data = s.recv(1024)
        print(f'Mensagem recebida de volta do Servidor: {data.decode()}')