echo "Transfering data to the general repository node."
sshpass -f password ssh sd207@l040101-ws01.ua.pt 'mkdir -p PullTheRope'
sshpass -f password ssh sd207@l040101-ws01.ua.pt 'rm -rf PullTheRope/*'
sshpass -f password scp dirGeneralRepos.zip sd207@l040101-ws01.ua.pt:PullTheRope

echo "Decompressing data sent to the general rrepository node."
sshpass -f password ssh sd207@l040101-ws01.ua.pt 'cd PullTheRope ; unzip -uq dirGeneralRepos.zip'

echo "Executing program at the server general repository."
sshpass -f password ssh sd207@l040101-ws01.ua.pt 'cd PullTheRope/dirGeneralRepos ; java ServerSide.main.ServerGeneralRepo 22260'
echo "General repository server shutdown"
sshpass -f password ssh sd207@l040101-ws01.ua.pt 'cd PullTheRope/dirGeneralRepos ; less logger'