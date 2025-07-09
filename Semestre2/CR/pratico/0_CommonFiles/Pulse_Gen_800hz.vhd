----------------------------------------------------------------------------------
-- Pulse Generator for 800 Hz
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

entity pulse_gen_800Hz is
    generic(MAX : positive := 125_000); -- 800 Hz
    Port ( clk   : in STD_LOGIC;
           reset : in STD_LOGIC;
           pulse : out STD_LOGIC);
end pulse_gen_800Hz;

architecture Behavioral of pulse_gen_800Hz is
    signal s_counter : natural range 0 to MAX-1;
begin
    process(clk)
    begin
        if rising_edge(clk) then
            pulse <= '0';
            if (reset = '1') then
                s_counter <= 0;
            else
            s_counter <= s_counter + 1;
                if (s_counter = MAX - 1) then
                    s_counter <= 0;
                    pulse <= '1';
                end if;
            end if;
        end if;
    end process;
end Behavioral;
