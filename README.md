# CreoInterferenceServer

Basic Interference analysis with PTC Creo's JLink api exposed as a server.

This is a utility package, meant to be used with [symbench-athens-client](https://github.com/symbench/symbench-athens-client).

## Usage
**Warning** :This library is still under active development and shouldn't be used in production.

### Prerequisites
1. JAVA (Tested with OpenJDK 11)
2. PTC Creo and JLink Object API. Link to install [here](http://simplifiedlogic.com/how-to-install-jlink-for-creo).
3. Set the environment variable `CREO_HOME` to point to creo installation directory (Windows `cmd` example below)
    ```shell
    > set CREO_HOME=C:\Program Files\PTC\Creo x.x.x.x
    ```
   
### Build and Run
Clone the repository and start the server.

```shell
> git clone https://github.com/symbench/CreoInterferenceServer.git
> cd CreoInterferenceServer
> .\gradlew.bat run
```

Finally, `api` server will be available at http://localhost:8000