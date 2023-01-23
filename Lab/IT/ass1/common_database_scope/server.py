import socket
from threading import Thread
import time
import json
import os
import hashlib

class Server:
    def __init__(self, host, port):
        self.host = host
        self.port = port
        self.keystore = {}
        self.userdata = {}
        self.restoreUserData()
        self.socket = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
        self.socket.bind((self.host, self.port))
        self.socket.listen(1)

    def restoreUserData(self):
        if os.path.exists("userdata.json"):
            with open("userdata.json", "r") as f:
                self.userdata = json.load(f)
        else:
            self.storeUserData()

    def storeUserData(self):
        with open("userdata.json", "w") as f:
            json.dump(self.userdata, f)

    def listenToConnections(self):
        while True:
            conn, addr = self.socket.accept()
            thread = Thread(target=self.handleConnection, args=(conn, addr))
            thread.setDaemon(True)
            thread.start()

    def handleConnection(self, conn:socket.socket, addr):
        authenticated = False
        username = ""
        while True:
            data = conn.recv(4048)
            if not data:
                break
            data = data.decode()
            msgtype = data.split("\n")[0]
            if msgtype == "--QUIT--":
                break
            data = data.split("\n", 1)[1].strip()
            if not authenticated:
                # If user not authenticated , he has only access to `AUTH` command 
                if msgtype == "--AUTH--":
                    username = data.split(":")[0]
                    password = data.split(":")[1]
                    hashed_password = hashlib.sha256(password.encode("utf-8")).hexdigest()
                    # Already existsed - login
                    if username in self.userdata:
                        # Check password
                        if self.userdata[username][0] == hashed_password:
                            authenticated = True
                            conn.sendall("authenticated".encode())
                        else:
                            conn.sendall("invalid credentials".encode())
                            break
                    else:
                        # Create account
                        self.userdata[username] = [hashed_password, "guest"]
                        self.storeUserData()
                        authenticated = True
                        conn.sendall("authenticated".encode())
            else:
                if msgtype == "--QUERY--":
                    queries = data.split("\n")
                    returnval = ""
                    for query in queries:
                        if query.startswith("get"):
                            key = query.split("\t")[1]
                            # Fetch value from store
                            value = self.getValue(key, username) + "\n"
                            # Send value
                            returnval = returnval + value + "\n"
                        elif query.startswith("put"):
                            key = query.split("\t")[1]
                            value = query.split("\t")[2]
                            # Put value to store
                            self.putValue(key, value, username)

                    returnval = returnval +"--END--\n"
                    conn.sendall(returnval.encode())
                    time.sleep(0.3)

        # Close the connection
        conn.close()

    def putValue(self, key, value, username):
        self.keystore[key] = [value, username]
    
    def getValue(self, key, username):
        try:
            x =  self.keystore[key]
            # Verify manager
            self.restoreUserData()
            if self.userdata[username][1] == "manager":
                return x[0]
            # Guest user
            if x[1] == username:
                return x[0]
            else:
                return ""
        except:
            return ""
        

    def close(self):
        self.socket.close()


if __name__ == "__main__":
    server = Server("127.0.0.1", 6000)
    try:
        server.listenToConnections()
    except KeyboardInterrupt:
        print("Closing server")
        server.close()