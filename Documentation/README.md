# COMP2000Assessment

## UML Diagram
![[UML Diagram ver 3.png]]

## Forest Fire Simulation
Java simulation of how weather (lightning, rain, wind) affect fire spread across a grid. The grid is a 2D Array cell where each cell has some kind of terrain (river, vegetation type (tree, grass)). Fire spreads based on heat, fuel, and weather that is affecting the grid. The simulation runs in Swing, with JPanel inside a JFrame.


### Structure
* App is the starting point, where it sets up the Swing window and buttons, and starts the simulation loop with a Timer.

* ForestFireSimulation is the core of the whole thing. It holds the grid of cells, a separate heat map, and weather that is currently active. Each tick (Timer) it :
- spreads heat  from burning cells to their neighbours
- applies the active weather to the grid
- updates fire intensity and vegetation fuel
- ignites any cells that is hot enough
- lets terrain evolve.

* PaintPanel draws the current state of the grids.

### The classes

* Grid<T> : Stores values by row/column and knows how to find neighbouring positions

* Cell : One square of the grid. It has a Terrain and maybe a Fire, and updates whether the terrain on it is on fire or not.

* Terrain: Abstract class, the base for anything a cell can hold. By default `update()` does nothing, since most terrain doesn't change on its own.

* River: Extends Terrain and adds a `coolingStrength`, used to cool down nearby heat.

* Vegetation: Extends Terrain and implements `Burnable`, which is where most of the logic lives. Vegetation itself is an abstract. Age, fuel, burn rate, and moisture are shared by every vegetation type, with abstract method like calculateSpreadHeat(), update()

* Tree and Grass: Extend Vegetation and provide their own version of those two methods

* Burnable: Interface that defines what anything burnable needs: `burn()`, `isBurnedOut()`, `isBurning()`, `ignite()`. Vegetation is the only class that implements it and provides the actual logic.

* Fire: Tracks how intense a fire is in a cell. It can strengthen or weaken, and knows when it's burnt out.

* Weather: Abstract, representing something that affects the whole grid for a period of time (has a strength, duration, and area it hits). Each subclass does its own thing
 
* Heatwave: Gradually adds heat

* Rain: Removes heat

* Wind: Pushes heat in a direction

* Lightning: Randomly strikes burnable cells


