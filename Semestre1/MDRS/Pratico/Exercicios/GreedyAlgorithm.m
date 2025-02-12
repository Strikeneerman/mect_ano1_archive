function [bestSol,bestObjective,noCycles,avObjective] = GreedyAlgorithm(nNodes,Links,T,sP,nSP,timeLimit)
    t= tic;
    nFlows= size(T,1);
    bestObjective= inf;
    noCycles= 0;
    aux= 0;
    while toc(t) < timeLimit

        sol = GR(nFlows,nSP,nNodes,Links,T,sP);
        
        Loads= calculateLinkLoads(nNodes,Links,T,sP,sol);
        load= max(max(Loads(:,3:4)));
        noCycles= noCycles+1;
        aux= aux+load;
        if load<bestObjective
            bestSol= sol;
            bestObjective= load;
        end
    end
    avObjective= aux/noCycles;
end


 function sol = GR(nFlows,nSP,nNodes,Links,T,sP)
        sol = zeros(1,nFlows);
        randFlows = randperm(nFlows);
        for f= randFlows
            temp = inf;
            for p= 1:nSP(f)
                sol(f)=p;
                Loads= calculateLinkLoads(nNodes,Links,T,sP,sol);
                load= max(max(Loads(:,3:4)));
                if load<temp
                    temp= load;
                    best_p = p;
                end
            end
            sol(f)= best_p;
        end
end