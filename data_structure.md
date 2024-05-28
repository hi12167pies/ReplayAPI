# Data types

- VarInt - Same as minecraft VarInt
- String - Array of char prefixed with VarInt for length

# Structure of replay file

| Name             | Type                         | Notes                   |
|------------------|------------------------------|-------------------------|
| Codec Version    | VarInt                       |
| Entity Info Size | VarInt                       | Size of EntityInfo list |
| Entity Info      | [EntityInfo[]](#entity-info) | List of entity info     |

# Entity Info

| Name      | Type   | Notes                                                         |
|-----------|--------|---------------------------------------------------------------|
| Entity Id | VarInt | This is any number, it is used for identifying entities later |
| Name      | String |