package Model.Generators;

public interface FlowGenerator {
    /**
     * Decides if something should be generated based on some criteria (i.e. time passed since last generation, random number value, ...)
     * @return true if something should be generated now, false if not
     */
    boolean shouldGenerate();
}
