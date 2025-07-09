--------------------------------------------
-- Module Name: tutorial
--------------------------------------------

library IEEE;
use IEEE.STD_LOGIC_1164.ALL;
use IEEE.STD_LOGIC_ARITH.ALL;
use IEEE.STD_LOGIC_UNSIGNED.ALL;
use IEEE.numeric_std.ALL;

use STD.textio.all;
use IEEE.std_logic_textio.all;

library UNISIM;
use UNISIM.VComponents.all;

Entity tutorial_tb Is
end tutorial_tb;

Architecture behavior of tutorial_tb Is
	Component tutorial
	port (
		sw  : in STD_LOGIC_VECTOR(3 downto 0);
		led : out STD_LOGIC_VECTOR(15 downto 0)
		);	
	End Component;
	
	Signal switch : STD_LOGIC_VECTOR(3 downto 0) := X"0";
	Signal led_out : STD_LOGIC_VECTOR(15 downto 0) := X"0000";
	Signal led_exp_out : STD_LOGIC_VECTOR(15 downto 0) := X"0000";
		
	Signal count_int_2 : STD_LOGIC_VECTOR(3 downto 0) := X"0";

	procedure expected_led (
		sw_in : in std_logic_vector(3 downto 0);
		led_expected : out std_logic_vector(15 downto 0)
	) is
		   
	begin	
	
	   led_expected := "0000000000000000";	
	 
       case sw_in is
            when "0000" => led_expected := "0000000000000001";
            when "0001" => led_expected := "0000000000000010";
            when "0010" => led_expected := "0000000000000100";
            when "0011" => led_expected := "0000000000001000";
            when "0100" => led_expected := "0000000000010000";
            when "0101" => led_expected := "0000000000100000";
            when "0110" => led_expected := "0000000001000000";
            when "0111" => led_expected := "0000000010000000";
            when "1000" => led_expected := "0000000100000000";
            when "1001" => led_expected := "0000001000000000";
            when "1010" => led_expected := "0000010000000000";
            when "1011" => led_expected := "0000100000000000";
            when "1100" => led_expected := "0001000000000000";
            when "1101" => led_expected := "0010000000000000";
            when "1110" => led_expected := "0100000000000000";
            when "1111" => led_expected := "1000000000000000";
            when others => led_expected := "0000000000000000";
            
        end case;
        
	end expected_led;
	
begin
	uut:  tutorial PORT MAP (
			sw  => switch,
			led => led_out
		 );
		 
	process
		variable s : line;
		variable i : integer := 0;
		variable count : integer := 0;
	    variable proc_out : STD_LOGIC_VECTOR(15 downto 0);

	begin
        for i in 0 to 127 loop   
	      count := count + 1;
	               	  
		  wait for 50 ns;
		  switch <= count_int_2;
		  
		  wait for 10 ns;
		  expected_led (switch, proc_out);
		  led_exp_out <= proc_out;

		  -- If the outputs match, then announce it to the simulator console.
          if (led_out = proc_out) then
                write (s, string'("LED output MATCHED at ")); write (s, count ); write (s, string'(". Expected: ")); write (s, proc_out); write (s, string'(" Actual: ")); write (s, led_out); 
                writeline (output, s);
          else
              write (s, string'("LED output mis-matched at ")); write (s, count); write (s, string'(". Expected: ")); write (s, proc_out); write (s, string'(" Actual: ")); write (s, led_out); 
              writeline (output, s);
          end if;
		  		  
		  -- Increment the switch value counters.
		  count_int_2 <= count_int_2 + 2;
        end loop;		 
       
	end process;
end behavior;
