# kv4p-ht
Open source handheld ham radio project kv4p HT

Please see the main project site: https://kv4p.com


https://gemini.google.com/share/1353b3323fda


פקודת הרצה של הדוקר לסגירת KV4P GMDSS-debug.apk

POWERSHELL 
צריך להיות בספריה 
..\kv4p-ht-GMDSS\android-src\KV4PHT>

docker run --rm -v "${PWD}:/project" -w /project mingc/android-build-box bash -c "chmod +x gradlew && ./gradlew assembleDebug"


Outcome Path:

..\kv4p-ht-GMDSS\android-src\KV4PHT\app\build\outputs\apk\debug\

