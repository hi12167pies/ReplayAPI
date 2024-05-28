# Data types

- VarInt - Same as minecraft VarInt
- String - Array of char prefixed with VarInt for length

# Structure of replay file

| Name             | Type                         | Notes                   |
|------------------|------------------------------|-------------------------|
| Codec Version    | VarInt                       |
| Length           | VarInt                       |
| Entity Info Size | VarInt                       | Size of EntityInfo list |
| Entity Info      | [EntityInfo[]](#entity-info) | List of entity info     |
| Tick data size   | VarInt                       |
| Tick data        | [TickData[]](#tick-data)     |

# Entity Info

| Name      | Type   | Notes                                                         |
|-----------|--------|---------------------------------------------------------------|
| Entity Id | VarInt | This is any number, it is used for identifying entities later |
| Name      | String |

# Tick Data

| Name        | Type                        | Notes                                                        |
|-------------|-----------------------------|--------------------------------------------------------------|
| Tick Number | VarInt                      |
| Data        | [Recordable[]](#recordable) |

# Recordable
| Name            | Type                             | Notes                                 |
|-----------------|----------------------------------|---------------------------------------|
| Recordable Type | VarInt                           | [recordable types](#recordable-types) |
| Data            | any, depends on which recordable |                                       |

# Recordable Types
| Id | Recordable          |
|----|---------------------|
| 1  | Location Recordable |