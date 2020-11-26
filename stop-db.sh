#!/bin/bash
cd docker/topologies/db || exit
docker-compose down

#abandonned clearing option:
#docker exec gamification_db mysqldump -d -ugamification_user -pgamification_secret --no-tablespaces --add-drop-table gamifi > gamifi.sql &&
#  mysql -ugamification_user -pgamification_secret gamifi < gamifi.sql