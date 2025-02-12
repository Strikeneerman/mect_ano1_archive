echo "Compiling source code."
javac -source 8 -target 8 -cp lib/genclass.jar */*.java */*/*.java
if [ $? -eq 0 ]; then
    echo "Compilation successful."
else
    echo "Compilation failed."
fi
echo "Distributing intermediate code to the different execution environments."
echo "  General Repository of Information"
rm -rf dirGeneralRepos
mkdir -p dirGeneralRepos dirGeneralRepos/ServerSide dirGeneralRepos/ServerSide/main dirGeneralRepos/ServerSide/entities dirGeneralRepos/ServerSide/sharedRegions \
         dirGeneralRepos/ClientSide dirGeneralRepos/ClientSide/entities dirGeneralRepos/commInfra dirGeneralRepos/lib
# Copy server side code to the dirGeneralRepos
cp lib/genclass.jar dirGeneralRepos/lib
cp ServerSide/main/SimulPar.class ServerSide/main/ServerGeneralRepo.class dirGeneralRepos/ServerSide/main
cp ServerSide/entities/GeneralRepoClientProxy.class dirGeneralRepos/ServerSide/entities
cp ServerSide/sharedRegions/IGeneralRepo.class ServerSide/sharedRegions/MGeneralRepo.class dirGeneralRepos/ServerSide/sharedRegions
cp ClientSide/entities/CoachStates.class ClientSide/entities/RefereeStates.class ClientSide/entities/ContestantStates.class dirGeneralRepos/ClientSide/entities
cp commInfra/Message.class commInfra/MessageType.class commInfra/MessageException.class commInfra/ServerCom.class dirGeneralRepos/commInfra

#create a directory to store the intermediate code for the bench
echo "  Bench"
rm -rf dirBench
mkdir -p dirBench dirBench/ServerSide dirBench/ServerSide/main dirBench/ServerSide/entities dirBench/ServerSide/sharedRegions \
         dirBench/ClientSide dirBench/ClientSide/entities dirBench/ClientSide/stubs dirBench/commInfra
cp ServerSide/main/SimulPar.class ServerSide/main/ServerBench.class dirBench/ServerSide/main
cp ServerSide/entities/BenchClientProxy.class dirBench/ServerSide/entities
cp ServerSide/sharedRegions/IBench.class ServerSide/sharedRegions/MBench.class dirBench/ServerSide/sharedRegions
cp ClientSide/entities/CoachStates.class ClientSide/entities/CoachCloning.class ClientSide/entities/ContestantCloning.class ClientSide/entities/ContestantStates.class dirBench/ClientSide/entities
cp ClientSide/stubs/SGeneralRepo.class dirBench/ClientSide/stubs
cp commInfra/*.class dirBench/commInfra

#create a directory to store the intermediate code for the playground
echo "  Playground"
rm -rf dirPlayground
mkdir -p dirPlayground dirPlayground/ServerSide dirPlayground/ServerSide/main dirPlayground/ServerSide/entities dirPlayground/ServerSide/sharedRegions \
         dirPlayground/ClientSide dirPlayground/ClientSide/entities dirPlayground/ClientSide/stubs dirPlayground/commInfra
cp ServerSide/main/SimulPar.class ServerSide/main/ServerPlayground.class dirPlayground/ServerSide/main
cp ServerSide/entities/PlaygroundClientProxy.class dirPlayground/ServerSide/entities
cp ServerSide/sharedRegions/IPlayground.class ServerSide/sharedRegions/MPlayground.class dirPlayground/ServerSide/sharedRegions
cp ClientSide/entities/CoachStates.class ClientSide/entities/RefereeStates.class ClientSide/entities/ContestantStates.class ClientSide/entities/CoachCloning.class ClientSide/entities/RefereeCloning.class ClientSide/entities/ContestantCloning.class dirPlayground/ClientSide/entities
cp ClientSide/stubs/SGeneralRepo.class dirPlayground/ClientSide/stubs
cp commInfra/*.class dirPlayground/commInfra

#create a directory to store the intermediate code for the referee site
echo "  Referee Site"
rm -rf dirRefereeSite
mkdir -p dirRefereeSite dirRefereeSite/ServerSide dirRefereeSite/ServerSide/main dirRefereeSite/ServerSide/entities dirRefereeSite/ServerSide/sharedRegions \
         dirRefereeSite/ClientSide dirRefereeSite/ClientSide/entities dirRefereeSite/ClientSide/stubs dirRefereeSite/commInfra
cp ServerSide/main/SimulPar.class ServerSide/main/ServerRefereeSite.class dirRefereeSite/ServerSide/main
cp ServerSide/entities/RefereeSiteClientProxy.class dirRefereeSite/ServerSide/entities
cp ServerSide/sharedRegions/IRefereeSite.class ServerSide/sharedRegions/MRefereeSite.class dirRefereeSite/ServerSide/sharedRegions
cp ClientSide/entities/CoachStates.class ClientSide/entities/CoachCloning.class ClientSide/entities/RefereeCloning.class ClientSide/entities/RefereeStates.class dirRefereeSite/ClientSide/entities
cp ClientSide/stubs/SGeneralRepo.class dirRefereeSite/ClientSide/stubs
cp commInfra/*.class dirRefereeSite/commInfra

#----   CLIENT SIDE CODE ------
#create a directory to store the intermediate code for the coach
echo "  Coach"
rm -rf dirCoach
mkdir -p dirCoach dirCoach/ServerSide dirCoach/ServerSide/main \
         dirCoach/ClientSide dirCoach/ClientSide/entities dirCoach/ClientSide/main dirCoach/commInfra dirCoach/ClientSide/stubs
cp ServerSide/main/SimulPar.class dirCoach/ServerSide/main
cp ClientSide/main/clientCoach.class dirCoach/ClientSide/main
cp ClientSide/entities/TCoach.class ClientSide/entities/CoachStates.class dirCoach/ClientSide/entities
cp ClientSide/stubs/SGeneralRepo.class ClientSide/stubs/SBench.class ClientSide/stubs/SPlayGround.class ClientSide/stubs/SRefereeSite.class dirCoach/ClientSide/stubs
cp commInfra/Message.class commInfra/MessageType.class commInfra/MessageException.class commInfra/ClientCom.class dirCoach/commInfra

#create a directory to store the intermediate code for the contestant
echo "  Contestant"
rm -rf dirContestant
mkdir -p dirContestant dirContestant/ServerSide dirContestant/ServerSide/main \
         dirContestant/ClientSide dirContestant/ClientSide/entities dirContestant/ClientSide/main dirContestant/commInfra dirContestant/ClientSide/stubs
cp ServerSide/main/SimulPar.class dirContestant/ServerSide/main
cp ClientSide/main/clientContestant.class dirContestant/ClientSide/main
cp ClientSide/entities/TContestant.class ClientSide/entities/ContestantStates.class dirContestant/ClientSide/entities
cp ClientSide/stubs/SGeneralRepo.class ClientSide/stubs/SBench.class ClientSide/stubs/SPlayGround.class dirContestant/ClientSide/stubs
cp commInfra/Message.class commInfra/MessageType.class commInfra/MessageException.class commInfra/ClientCom.class dirContestant/commInfra

#create a directory to store the intermediate code for the referee
echo "  Referee"
rm -rf dirReferee
mkdir -p dirReferee dirReferee/ServerSide dirReferee/ServerSide/main \
         dirReferee/ClientSide dirReferee/ClientSide/entities dirReferee/ClientSide/main dirReferee/commInfra dirReferee/ClientSide/stubs
cp ServerSide/main/SimulPar.class dirReferee/ServerSide/main
cp ClientSide/main/clientReferee.class dirReferee/ClientSide/main
cp ClientSide/entities/TReferee.class ClientSide/entities/RefereeStates.class dirReferee/ClientSide/entities
cp ClientSide/stubs/SGeneralRepo.class ClientSide/stubs/SBench.class ClientSide/stubs/SPlayGround.class ClientSide/stubs/SRefereeSite.class dirReferee/ClientSide/stubs
cp commInfra/Message.class commInfra/MessageType.class commInfra/MessageException.class commInfra/ClientCom.class dirReferee/commInfra

echo "Compressing distribution files."
rm -f dirGeneralRepos.zip 
rm -f dirBench.zip 
rm -f dirPlayground.zip 
rm -f dirRefereeSite.zip 
rm -f dirCoach.zip 
rm -f dirContestant.zip 
rm -f dirReferee.zip
zip -rq dirGeneralRepos.zip dirGeneralRepos
zip -rq dirBench.zip dirBench
zip -rq dirPlayground.zip dirPlayground
zip -rq dirRefereeSite.zip dirRefereeSite
zip -rq dirCoach.zip dirCoach
zip -rq dirContestant.zip dirContestant
zip -rq dirReferee.zip dirReferee
echo "Distribution files compressed."

#rm -rf dirGeneralRepos dirBench dirPlayground dirRefereeSite dirCoach dirContestant dirReferee

# echo "Deploying and decompressing execution environments."
# mkdir -p PullTheRope
# rm -rf PullTheRope/*
# cp dirGeneralRepos.zip dirBench.zip dirPlayground.zip dirRefereeSite.zip dirCoach.zip dirContestant.zip dirReferee.zip PullTheRope
# cd PullTheRope
# unzip -q dirGeneralRepos.zip
# unzip -q dirBench.zip
# unzip -q dirPlayground.zip
# unzip -q dirRefereeSite.zip
# unzip -q dirCoach.zip
# unzip -q dirContestant.zip
# unzip -q dirReferee.zip
# rm -f dirGeneralRepos.zip dirBench.zip dirPlayground.zip dirRefereeSite.zip dirCoach.zip dirContestant.zip dirReferee.zip
# cd ..
# rm -f dirGeneralRepos.zip dirBench.zip dirPlayground.zip dirRefereeSite.zip dirCoach.zip dirContestant.zip dirReferee.zip


