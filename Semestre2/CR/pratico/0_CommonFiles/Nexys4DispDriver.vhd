----------------------------------------------------------------------------------
-- Nexys4DispDriver
----------------------------------------------------------------------------------

library IEEE;
use IEEE.STD_LOGIC_1164.ALL;
use IEEE.NUMERIC_STD.ALL;

entity Nexys4DispDriver is
    Port (
        clk_enable      : in  STD_LOGIC;
        clk             : in  STD_LOGIC;
        digit_enable    : in  STD_LOGIC_VECTOR (7 downto 0);
        dp_enable       : in  STD_LOGIC_VECTOR (7 downto 0);
        value_display   : in  STD_LOGIC_VECTOR (31 downto 0);
        an              : out STD_LOGIC_VECTOR (7 downto 0);
        seg             : out STD_LOGIC_VECTOR (6 downto 0);
        dp              : out STD_LOGIC
    );
end Nexys4DispDriver;

architecture Behavioral of Nexys4DispDriver is

    signal digit_counter: unsigned(3 downto 0) := (others => '0');
    signal decoded_seg  : STD_LOGIC_VECTOR (6 downto 0);
    signal tmp_value  : STD_LOGIC_VECTOR (3 downto 0);

begin

    process(clk)
    variable tmp_value : STD_LOGIC_VECTOR(3 downto 0);
    begin
        if (clk_enable = '1') then
            if rising_edge(clk) then
                
                if (digit_counter = 7) then
                    digit_counter <= (others => '0');
                else
                    digit_counter <= digit_counter +1;
                end if;
                
                -- digit_enable
                -- an
                an <= (others => '1');
                if(digit_enable(to_integer(digit_counter)) =  '1') then
                    an(to_integer(digit_counter)) <= '0';
                end if;
                
                -- dp_enable
                -- dp
                if (dp_enable(to_integer(digit_counter)) = '1') then
                    dp <= '0';
                else
                    dp <= '1';
                end if;
                
                -- value_displays
                -- seg 
                tmp_value := value_display(to_integer(digit_counter)*4 + 3 downto to_integer(digit_counter)*4);
                case tmp_value is
                    when "0000" => decoded_seg <= "1000000"; -- 0
                    when "0001" => decoded_seg <= "1111001"; -- 1
                    when "0010" => decoded_seg <= "0100100"; -- 2
                    when "0011" => decoded_seg <= "0110000"; -- 3
                    when "0100" => decoded_seg <= "0011001"; -- 4
                    when "0101" => decoded_seg <= "0010010"; -- 5
                    when "0110" => decoded_seg <= "0000010"; -- 6
                    when "0111" => decoded_seg <= "1111000"; -- 7
                    when "1000" => decoded_seg <= "0000000"; -- 8
                    when "1001" => decoded_seg <= "0010000"; -- 9
                    when "1010" => decoded_seg <= "0001000"; -- A
                    when "1011" => decoded_seg <= "0000011"; -- B
                    when "1100" => decoded_seg <= "1000110"; -- C
                    when "1101" => decoded_seg <= "0100001"; -- D
                    when "1110" => decoded_seg <= "0000110"; -- E
                    when "1111" => decoded_seg <= "0001110"; -- F
                    when others => decoded_seg <= "1111111"; -- Apagado
                end case;
                seg <= decoded_seg;
            end if;
        end if;
    end process;
end Behavioral;