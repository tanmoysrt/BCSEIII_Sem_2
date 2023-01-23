import os
import json
import sys

if __name__ == "__main__":
    username = sys.argv[1]
    role = sys.argv[2]
    config = json.loads(open("userdata.json", "r").read())
    if username in config:
        config[username][1] = role
        with open("userdata.json", "w") as file:
            file.write(json.dumps(config))
        print("Role updated !")
    else:
        print(f"User with username {username} does not exists")