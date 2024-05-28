# Structure of replay file

| Name          | Type                         | Notes               |
|---------------|------------------------------|---------------------|
| codec version | int                          |
| entity info   | [entityInfo](#entity-info)[] | List of entity info |

# Entity Info

| Name   | Type  | Notes               |
|--------|-------|---------------------|
| int    | id    |
| string | name  |