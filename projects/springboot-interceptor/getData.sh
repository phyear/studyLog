preffix="http://localhost:8080/"
array=(
    'test'
    'order'
)

for suffix in $array
do 
 curl -v -L $preffix+$suffix
 done





