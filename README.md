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


### Important Notes
At this point, new CREO can't directly be started from this repository. So, you have to run creo manually.


## API example
To maintain a consistent api with [CREOSON](https://github.com/SimplifiedLogic/creoson). I have burrowed concepts from the creoson repository to add an interference command handler.

Currently, this handler only computes global interferences via the `global_interference` function.

### Request Example

```json
{
   "command": "interference", 
   "function": "global_interference",
   "data": {
      "assembly_path": "C:\\MyDocuments\\PathToAnAssembly\\assembly.asm"
   }
}
```

Note: If `assembly_path` is `null`, there current active model in creo is used for calculations.

The response is as follows. It returns interfering parts and the interference volume.

```json
{
   "data": {
      "interferences": [
         {
            "part_2_name": "...",
            "part_1_name": "...",
            "interference_volume": 2948309
         }
      ],
      "num_interferences": 1
   },
   "status": {
      "error": false
   }
}
```