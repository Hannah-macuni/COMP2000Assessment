# COMP2000Assessment

## UML Diagram
![[UML Diagram ver 3.png]]

## Forest Fire Simulation
Java simulation of how weather (lightning, rain, wind, heatwave) affect fire spread across a grid. The grid is a 2D array of cells, where each cell has some kind of terrain (river, or a vegetation type like tree or grass). Fire spreads based on heat, fuel, and whatever weather is currently affecting the grid. The simulation runs in Swing, with a JPanel inside a JFrame.

### Structure
* App is the starting point — it sets up the Swing window and buttons, and starts the simulation loop with a Timer.

* ForestFireSimulation is the core of the whole thing. It holds the grid of cells, a separate heat map, and a WeatherManager. Each tick (Timer) it:
- updates all active weather events via the WeatherManager
- spreads heat from burning cells to their neighbours
- applies river cooling to nearby cells
- updates fire intensity and vegetation fuel
- ignites any cells that are hot enough
- lets terrain evolve

* PaintPanel draws the current state of the grid, coloring each cell based on its terrain type and fire intensity.

### The classes

* Grid<T>: Stores values by row/column and knows how to find neighbouring positions.

* Cell: One square of the grid. Has a Terrain and maybe a Fire, and tracks whether it's currently burning.

* Terrain: Abstract base class for anything a cell can hold. Holds a `FlammableStrategy` field, which it delegates `canBurn()` and `isBurnedOut()` to. `update()` is abstract, so every terrain type defines its own behaviour.

* River: Extends Terrain, uses `NonBurnable` as its flammable strategy (so it never burns), and adds a `coolingStrength` used to cool down nearby heat.

* Vegetation: Extends Terrain. Holds shared state — age and moisture — and delegates fuel/burn rate tracking to a `Burnable` object (from the Flammability package). Declares an abstract `calculateSpreadHeat()` for subclasses to implement, and overrides `update()` to age the vegetation each tick.

* Tree and Grass: Extend Vegetation and provide their own `calculateSpreadHeat()` — Tree adds a height bonus that grows over time, Grass factors in density. Both are given randomized starting stats (age, fuel, burn rate, moisture, height/density) when the grid is generated, so no two cells look exactly alike.

* Flammability package:
  - `FlammableStrategy`: interface describing burn-related behaviour (`canBurn()`, `isBurnedOut()`), used by Terrain.
  - `Burnable`: implementation used by Vegetation — tracks fuel and burn rate.
  - `NonBurnable`: implementation used by River — always reports as non-burnable.

* Fire: Tracks how intense a fire is in a cell. Can strengthen or weaken, and knows when it's burnt out.

* Weather: Abstract class representing something that affects the grid for a period of time (has a strength, duration, and location it targets). Each subclass implements its own `affectSimulation()`.

* WeatherManager: Holds a list of currently active weather events. Each tick, it decrements each event's duration, removes any that have expired, and triggers `affectSimulation()` on the ones still active. Also creates new Weather objects (via `createWeather()`) when the user applies weather through the GUI.

* Heatwave: Adds heat directly to specific locations.

* Rain: Removes heat from specific locations.

* Wind: Tells the simulation to push heat toward whichever neighbour is downwind of any burning cell, based on a chosen direction.

* Lightning: Randomly strikes burnable cells within its target locations.