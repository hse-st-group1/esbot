
#!/bin/bash

docker exec -i esbot-db psql -U esbot_user -d esbot <<EOF
INSERT INTO users (userid, user_name)
VALUES (
    '11111111-1111-1111-1111-111111111111',
    'Test-User'
);

SELECT * FROM users; 
EOF