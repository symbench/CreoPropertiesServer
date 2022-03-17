# CreoPropertiesServer

Basic properties' analysis with PTC Creo's JLink api exposed as a server.

While using [`creoson`](https://github.com/SimplifiedLogic/creoson), two short-comings were found in the project, which were an absolute business requirement for one of our projects.

1. The `file` command's `massprops` function doesn't return the center of mass. [**SimplifiedLogic/creoson#69**](https://github.com/SimplifiedLogic/creoson/issues/69)
2. Interference analysis is not possible. [**SimplifiedLogic/creoson#76**](https://github.com/SimplifiedLogic/creoson/issues/76)

So, this utility package is written following concepts from [`creoson`](https://github.com/SimplifiedLogic/creoson) to complement it such that aforementioned calculations are possible.
This package is meant to be used with [symbench-athens-client](https://github.com/symbench/symbench-athens-client).

## Usage
**Warning** :This package is still under active development and shouldn't be used in production.

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
To maintain a consistent api with [CREOSON](https://github.com/SimplifiedLogic/creoson). I have burrowed concepts from `creoson` to add interference and massproperties command handlers.

Currently, the `InterferenceHandler` only computes global interferences via the `global_interference` function.

The `MassPropertiesHandler` computes `massproperties` in a similar fashion as `creoson` but also returns an additional `center_of_mass`.

Note: If `assembly_path` is `null`, current active model in creo is used for calculations.

### Request Example: `InterferenceHandler`

```json
{
   "command": "interference", 
   "function": "global_interference",
   "data": {
      "assembly_path": "C:\\MyDocuments\\PathToAnAssembly\\assembly.asm"
   }
}
```

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

### Request Example: `MassPropertiesHandler`
```json
{
  "command": "file",
  "function": "massproperties",
  "data": {
      "assembly_path": null
  }
}
```

The response is as follows. It returns an additional `center_of_mass` data.

```json
{
    "data": {
        "volume": 1.3697542616974676E10,
        "ctr_grav_inertia_tensor": {
            "z_axis": {
                "x": -1.1956336301817415E9,
                "y": 0.0,
                "z": 4.554169353974172E10
            },
            "y_axis": {
                "x": -7.926659258691117E8,
                "y": 1.268469094825741E10,
                "z": 0.0
            },
            "x_axis": {
                "x": 3.4044945773416866E10,
                "y": -7.926659258691117E8,
                "z": -1.1956336301817412E9
            }
        },
        "density": 1.4205917324537666E-7,
        "coord_sys_inertia": {
            "z_axis": {
                "x": 1.6661677878862703E9,
                "y": -0.0,
                "z": 6.042445290753222E8
            },
            "y_axis": {
                "x": 7.926659258691117E8,
                "y": 3.345097418245059E10,
                "z": -0.0
            },
            "x_axis": {
                "x": 3.364272241781452E10,
                "y": 7.926659258691117E8,
                "z": 1.6661677878862703E9
            }
        },
        "coord_sys_inertia_tensor": {
            "z_axis": {
                "x": -1.6661677878862703E9,
                "y": 0.0,
                "z": 6.709369660026511E10
            },
            "y_axis": {
                "x": -7.926659258691117E8,
                "y": 3.4246966946889843E10,
                "z": 0.0
            },
            "x_axis": {
                "x": 3.405521871152591E10,
                "y": -7.926659258691117E8,
                "z": -1.6661677878862703E9
            }
        },
        "mass": 1945.8615796607355,
        "surface_area": 3.207324140708231E8,
        "center_of_mass": {
            "x": -3328.0347199090656,
            "y": 0.0,
            "z": -72.65932588011664
        }
    },
    "status": {
        "error": false
    }
}
```
