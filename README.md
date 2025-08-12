# v2 Branch

This branch is designed to be more flexible and allow for more features such as buffering to disk, or live replay that only records last 10 seconds (e.g. used for ban system)  

Currently, this branch is in development and should not be used unless you would like to experiment.

Breaking changes are very likely to happen

# Entity Types
Currently, the only entity supported is players, however this plugin will be designed for multiple entity types.

# Saving
Objects that need to be buffered will be serializable, otherwise it is expected the saver handles the object saving.