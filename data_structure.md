# Data types

- VarInt - Same as minecraft VarInt
- String - Array of char prefixed with VarInt for length

# Structure of replay file

| Name             | Type                         | Notes                   |
|------------------|------------------------------|-------------------------|
| Codec version    | VarInt                       |
| Length           | VarInt                       |
| Entity info size | VarInt                       | Size of EntityInfo list |
| Entity info      | [EntityInfo[]](#entity-info) | List of entity info     |
| Tick data size   | VarInt                       |
| Tick data        | [TickData[]](#tick-data)     |

# Entity info

| Name      | Type   | Notes                                                         |
|-----------|--------|---------------------------------------------------------------|
| Entity id | VarInt | This is any number, it is used for identifying entities later |
| Name      | String |

# Tick data

| Name        | Type                        | Notes                                                        |
|-------------|-----------------------------|--------------------------------------------------------------|
| Tick number | VarInt                      |
| Data        | [Recordable[]](#recordable) |

# Recordable
| Name            | Type                             | Notes                                 |
|-----------------|----------------------------------|---------------------------------------|
| Recordable type | VarInt                           | [Recordable types](#recordable-types) |
| Data            | any, depends on which recordable |                                       |

# Recordable types
| Id | Recordable          |
|----|---------------------|
| 1  | Location Recordable |