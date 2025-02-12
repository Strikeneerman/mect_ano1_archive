use IEEE.std_logic_1164.all;
use IEEE.numeric_std.all;
use IEEE.math_real.all; -- For log2 calculation

entity accumulator is
  generic (
    ADDR_BITS : integer range 2 to 8 := 4;
    DATA_BITS : integer range 4 to 32 := 8;
    DATA_BITS_LOG2 : integer := 3 
  );
  port (
    clock      : in  std_logic;
    write_addr : in  std_logic_vector(ADDR_BITS-1 downto 0);
    write_inc  : in  std_logic_vector(DATA_BITS-1 downto 0);
    read_addr  : in  std_logic_vector(ADDR_BITS-1 downto 0);
    read_data  : out std_logic_vector(DATA_BITS-1 downto 0)
  );
end accumulator;

architecture structural of accumulator is
  -- Internal signals
  type state_type is (IDLE, READ, ADD, WRITE);
  signal current_state, next_state : state_type := IDLE;
  
  signal s_write_addr_stable : std_logic_vector(ADDR_BITS-1 downto 0);
  signal s_write_inc_stable  : std_logic_vector(DATA_BITS-1 downto 0);
  signal s_aux_read_data     : std_logic_vector(DATA_BITS-1 downto 0);
  signal s_adder_result      : std_logic_vector(DATA_BITS-1 downto 0);
  signal s_carry_out         : std_logic;
  signal s_write_enable      : std_logic := '0';

begin
  -- Register for write address
  addr_reg: entity work.vector_register
    generic map (
      N => ADDR_BITS
    )
    port map (
      clock => clock,
    --  rst 	=> '0',
    --  en  	=> '1',
      D   	=> write_addr,
      Q   	=> s_write_addr_stable
    );

  -- Register for write increment
  inc_reg: entity work.vector_register
    generic map (
      N => DATA_BITS
    )
    port map (
      clock => clock,
     -- rst 	=> '0',
     -- en  	=> '1',
      D   	=> write_inc,
      Q   	=> s_write_inc_stable
    );

  -- Triple port RAM
  ram: entity work.triple_port_ram
    generic map (
      ADDR_BITS => ADDR_BITS,
      DATA_BITS => DATA_BITS
    )
    port map (
      clock         => clock,
      write_addr    => s_write_addr_stable,
      write_data    => s_adder_result,
      write_en      => s_write_enable,
      read_addr     => read_addr,
      read_data     => read_data,
      aux_read_addr => s_write_addr_stable,
      aux_read_data => s_aux_read_data
    );

  -- Adder
  adder: entity work.adder_n
    generic map (
      N => DATA_BITS
    )
    port map (
      a     => s_aux_read_data,
      b     => s_write_inc_stable,
      c_in  => '0',
      s     => s_adder_result,
      c_out => s_carry_out
    );
    
    
    process(clock)
    begin
      if rising_edge(clock) then
        current_state <= next_state;
      end if;
    end process;

    
     -- State Machine and Control Logic
    process (current_state)
    begin
        -- Default signal assignments (idle behavior)
        next_state  <= IDLE;
        s_write_enable <= '0';

        case current_state is
            when IDLE =>
                next_state <= READ;

            when READ =>
                -- Wait for the read data to stabilize, then move to addition
                next_state <= ADD;

            when ADD =>
                -- Perform addition
                next_state <= WRITE;

            when WRITE =>
                -- Enable write to RAM only during this state
                s_write_enable <= '1';
                next_state <= IDLE;

            when others =>
                next_state <= IDLE;
        end case;
    end process;

end structural;