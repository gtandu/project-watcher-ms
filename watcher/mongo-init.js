db.log.insertOne({"message": "Database created."});

db.createUser(
    {
        user: "admin",
        pwd: "pwd",
        roles: [
            {
                role: "readWrite",
                db: "watcher"
            }
        ]
    }
);
