import socket
import sys
import copy
from threading import Thread
import time
import json
import os


class Client:
    def __init__(self, host, port, interactive=False, storecred=False):
        self.host = host
        self.port = port
        self.socket = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
        self.socket.connect((self.host, self.port))
        self.queries = []
        self.interactive = interactive
        self.storecred = storecred

    def startProcess(self):
        self.authenticate()
        processQueryThread = Thread(target=self.processQueries, daemon=True)
        enqueueQueryThread = Thread(target=self.enqueueQuery, daemon=True)
        processQueryThread.start()
        if self.interactive:
            enqueueQueryThread.start()
        processQueryThread.join()
        if self.interactive:
            enqueueQueryThread.join()

    def authenticate(self):
        if os.path.exists("clientconfig.json"):
            with open("clientconfig.json", "r") as f:
                config = json.load(f)
            username = config["username"]
            password = config["password"]
        else:
            username = input("Username: ")
            password = input("Password: ")

        self.socket.sendall(f"--AUTH--\n{username}:{password}".encode())
        data = self.socket.recv(1024).decode()
        if data == "invalid credentials":
            print("Invalid credentials")
            sys.exit(0)
        elif data == "authenticated":
            if self.storecred:
                if not os.path.exists("clientconfig.json"):
                    with open("clientconfig.json", "w") as f:
                        f.write(json.dumps({
                            "username": username,
                            "password": password
                        }))
                    print("Saved credentials to clientconfig.json")
            return

    def enqueueQuery(self):
        while True:
            query = input()
            queries = query.split(" ")
            final_queries = []
            tmp = []
            for q in queries:
                if q in ["get", "put", "roleupdate"]:
                    if len(tmp) != 0:
                        final_queries.append(tmp)
                        tmp.clear()
                tmp.append(q)
            if len(tmp) != 0:
                final_queries.append(tmp)
            self.queries.extend(final_queries)

    def processQueries(self):
        while True:
            if len(self.queries) == 0:
                if self.interactive:
                    time.sleep(0.2)
                    continue
                break
            payload = "--QUERY--\n"
            query_copy = copy.deepcopy(self.queries)
            self.queries.clear()
            for query in query_copy:
                query_final = ""
                for i in range(0, len(query)):
                    query_final += query[i]
                    if i != len(query) - 1:
                        query_final += "\t"
                payload += query_final + "\n"
            # Send payload
            self.socket.sendall(payload.encode())
            # Read data
            data = ""
            while True:
                recv_data = self.socket.recv(1024).decode()
                if recv_data.endswith("--END--\n"):
                    data = data+recv_data.replace("--END--\n", "")
                    break
                else:
                    data = data+recv_data
            data = data.rstrip()
            print(data)
            # If not interactive, break
            if not self.interactive:
                self.socket.sendall("--QUIT--".encode())
                break
        
        self.close()

    def close(self):
        self.socket.close()
        sys.exit(0)

def argumentParser():
    args = sys.argv
    args = args[1:] # Delete first args that contains python file name

    data = {}
    data["host"] = args[0]
    data["port"] = int(args[1])
    data["interactive"] = len(args) > 2 and args[2] == "interactive"
    data["queries"] = []

    tmp = []
    starting = 2
    if data["interactive"]:
        starting = 3
    for i in range(starting, len(args)):
        if args[i] in ["get", "put", "roleupdate"]:
            # If tmp not empty
            if len(tmp) != 0:
                data["queries"].append(tmp)
                tmp = []            
        tmp.append(args[i])

    if len(tmp) != 0:
        data["queries"].append(tmp)

    return data


if __name__ == "__main__":
    data = argumentParser()
    client = Client(data["host"], data["port"], data["interactive"])
    client.queries = data["queries"]
    client.startProcess()
    client.close()