#maven build
cd badges-impl
mvn clean install
#vérifie la présence du .jar dans le dossier target
FILE=target/badges-impl-1.0.0.jar
if [ -f "${FILE}" ]; then
    #copie le .jar au même niveau que le dockerfile
    echo "${FILE} found, transfering..."
    mv "${FILE}" ../docker/images/app
    echo "${FILE} transfer finished"
    #construit l'image
    cd ../docker/images/app || exit
    docker build -t amt/gamification .
else
    >&2 echo "Error : ${FILE} not found"
    exit
fi

