function consumption = EnergyConsumption(Lengths,Loads)
    nInterfaces = 2;
    consumption = 0;
    
    auxLoads = Loads';
    for link = auxLoads
        if sum (link(3:4)) == 0
            consumption = consumption + 1;
        else
            distance = Lengths (link(1),link(2));
            consumption = consumption + (nInterfaces * 10) + (distance * 0.10); 
        end
    end
end


 