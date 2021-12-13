if [ -x "$(command -v docker)" ]; then
  echo "Docker installed"
else
  echo "docker not installed yet"
  exit 1
fi


if [ -z "$1" ]; then
  echo "Need to provide browser type, chromium or firefox"
  exit 1
fi
if [ -z "$2" ]; then
  echo "Need to provide Curam URL"
  exit 1
fi
if [ -z "$3" ]; then
  echo "Need to provide script name"
  exit 1
fi


echo "Browser is: $1" 
echo "URL is: $2" 
echo "SCRIPT is: $3"
echo "Current Dir is: $(pwd)"

# Print the program usage
# - calling code needs to exit with the appropriate status
printUsage() {
        echo ""
        echo "$run <Browser> <URL> <Script>"
        echo ""
}

docker run -t -v $(pwd)/scripts:/var/scripts -v $(pwd)/results:/var/results \
    -e SCRIPT=$3 -e URL=$2 -e BROWSER=$1 huangjien/t-cli:latest

