----------------------------------------------------------------------------------
-- Top Level Wrapper for TP2 part 1 
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

entity Top_Level_Wrapper is
    Port ( clk : in STD_LOGIC;
           btnC : in STD_LOGIC;
           led : out STD_LOGIC_VECTOR (3 downto 0));
end Top_Level_Wrapper;

architecture Behavioral of Top_Level_Wrapper is

    signal pulse_1Hz : STD_LOGIC;
    signal count_out : STD_LOGIC_VECTOR(3 downto 0);
    
    --Component declarations
    component pulse_gen_1Hz is
        generic(MAX : positive := 100_000_000);
        Port ( clk   : in STD_LOGIC;
               reset : in STD_LOGIC;
               pulse : out STD_LOGIC);
    end component;
    
    component counter is
    Port ( clk    : in STD_LOGIC;
           reset  : in STD_LOGIC;
           enable : in STD_LOGIC;
           count  : out STD_LOGIC_VECTOR(3 downto 0)
           );
    end component;


begin

    pulse_generator: pulse_gen_1Hz
        generic map (MAX => 100_000_000) 
        port map (
            clk   => clk,
            reset => btnC,
            pulse => pulse_1Hz
        );

    up_counter: counter
        port map (
            clk    => clk,
            reset  => btnC,
            enable => pulse_1Hz,  
            count  => count_out
        );

    led <= count_out;

end Behavioral;
