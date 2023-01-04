### Data structure to store data

*Dictionary of*
< key > := [ < value > , < username >]

### Data struccture to store user data
*Dictionary of*
< username > := [ < password > , < role >]

### Auth Flow
1. User send its username and password to the server
2. Server check if the username exists in userdata dictionary
3. If username exists, server check if the password hash is correct
4. thenn the session marked as authenticated.
5. If password incorrect, server close the connection
6. If username not exists, server create a new entry in userdata dictionary and authenticate the session

### Data Flow
1. primarily two commands `get key` and `set key value`
2. based on the role availble in userdata dictionary and username stored in key-value dictionary, server will allow the user to perform the operation. For manager role, server will allow to see all key-value pairs, for guest role, server will allow to see only the key-value pairs created by the user.

### CLI
1. Run server
```bash
python server.py
```
2. Connect client

```bash
python  client.py 127.0.0.1 5000 put fruit mango get fruit
```

For interactive mode
```bash
python  client.py 127.0.0.1 5000 interactive put fruit mango get fruit
```

### Role Update
1. Convert to manager
```bash
python roleupdate.py username manager
``` 
2. Convert to guest
```bash
python roleupdate.py username guest
```