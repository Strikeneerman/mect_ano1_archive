--------------------------------------------
-- Module Name: tutorial
--------------------------------------------

library IEEE;
use IEEE.STD_LOGIC_1164.ALL;

library UNISIM;
use UNISIM.VComponents.all;

Entity tutorial Is
port (
		sw  : in STD_LOGIC_VECTOR(3 downto 0);
		led : out STD_LOGIC_VECTOR(15 downto 0)
	);
end tutorial;

-- ffff

Architecture behavior of tutorial Is

begin
   
    process(sw)
    begin
    
    led <= (others => '0'); 
    
    case sw is
        when "0000" => led <= "0000000000000001";
        when "0001" => led <= "0000000000000010";
        when "0010" => led <= "0000000000000100";
        when "0011" => led <= "0000000000001000";
        when "0100" => led <= "0000000000010000";
        when "0101" => led <= "0000000000100000";
        when "0110" => led <= "0000000001000000";
        when "0111" => led <= "0000000010000000";
        when "1000" => led <= "0000000100000000";
        when "1001" => led <= "0000001000000000";
        when "1010" => led <= "0000010000000000";
        when "1011" => led <= "0000100000000000";
        when "1100" => led <= "0001000000000000";
        when "1101" => led <= "0010000000000000";
        when "1110" => led <= "0100000000000000";
        when "1111" => led <= "1000000000000000";    
        when others => led <= "0000000000000000";   
     end case;  
    end process;
   
end behavior;
		

