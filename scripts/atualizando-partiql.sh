aws dynamodb execute-statement --statement "UPDATE Music  \
                                            SET AlbumTitle='Updated Album Title'  \
                                            WHERE Artist='Acme Band' AND SongTitle='Happy Day' \
                                            RETURNING ALL NEW *"