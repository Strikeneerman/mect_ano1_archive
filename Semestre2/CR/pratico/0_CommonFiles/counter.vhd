----------------------------------------------------------------------------------
-- Counter
----------------------------------------------------------------------------------


library IEEE;
use IEEE.STD_LOGIC_1164.ALL;

-- Uncomment the following library declaration if using
-- arithmetic functions with Signed or Unsigned values
use IEEE.NUMERIC_STD.ALL;

-- Uncomment the following library declaration if instantiating
-- any Xilinx leaf cells in this code.
--library UNISIM;
--use UNISIM.VComponents.all;

entity counter is
    Port ( clk : in STD_LOGIC;
           reset : in STD_LOGIC;
           enable : in STD_LOGIC;
           count : out STD_LOGIC_VECTOR(3 downto 0)
           );
end counter;

architecture Behavioral of counter is
    
    signal counter : unsigned(3 downto 0):= "0000";
   -- signal counter : STD_LOGIC_VECTOR(3 downto 0) := (others => '0');

begin
    process(clk)
    begin
        if rising_edge(clk) then
            if (reset = '1') then
                counter <= (others => '0');
            elsif (enable = '1') then
                counter <= counter + 1;
            end if;
        end if; 
    end process;
    count <= std_logic_vector(counter);
end Behavioral;
