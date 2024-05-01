import java.util.Random;

public class MuscleFiber {
    private double fiberSize;
    private double maxSize;
    private double anabolicHormone;
    private double catabolicHormone;


    MuscleFiber(int percentage_slow_twitch_fibers) {
        this.anabolicHormone = 50;
        this.catabolicHormone = 52;
        Random random = new Random();

        // Initialize the fiberSize and maxSize randomly
        int max_size = 4;
        // with median dependent on % of slow twitch fibers.
        for (int i = 0; i < 20; i++) {
            if (random.nextInt(100) > percentage_slow_twitch_fibers) {
                max_size++;
            }
        }
        // provide non-uniform starting sizes for varied results, everyone's different
        this.fiberSize = (0.2 + random.nextDouble(0.4)) * max_size;
        this.maxSize = max_size;
        regulateMuscleFibers();
    }

    private void performDailyActivity() {
        this.catabolicHormone += 2.0 * Math.log(this.fiberSize) / Math.log(10);
        this.anabolicHormone += 2.5 * Math.log(this.fiberSize) / Math.log(10);

    }

    private void liftWeights(int intensity) {
        Random random = new Random();
        double percentageIntensity = intensity;
        percentageIntensity /= 100;

        if (random.nextDouble() < percentageIntensity * percentageIntensity) {
            this.anabolicHormone += Math.log(this.fiberSize) / Math.log(10) * 55;
            this.catabolicHormone += Math.log(this.fiberSize) / Math.log(10) * 44;
        }
    }
    private void sleep(double hours_of_sleep) {

        this.catabolicHormone -= 0.5 * Math.log(this.catabolicHormone) / Math.log(10) * hours_of_sleep;
        this.anabolicHormone -= 0.48 * Math.log(this.anabolicHormone) / Math.log(10) * hours_of_sleep;
    }

    public void performActivities(int ticks, boolean lift, int days_between_workouts, int intensity, double hours_of_sleep) {
        performDailyActivity();
        if (lift && (ticks % days_between_workouts == 0)) {
            liftWeights(intensity);
        }
        sleep(hours_of_sleep);
    }

    private void regulateMuscleFibers() {
        if (this.fiberSize < 1){
            this.fiberSize = 1;
        }
        if (this.fiberSize > this.maxSize){
            this.fiberSize = this.maxSize;
        }
    }

    public void regulateHormones() {
        this.anabolicHormone = Math.min(anabolicHormone, Params.MAX_ANABOLIC_HORMONE);
        this.anabolicHormone = Math.max(anabolicHormone, Params.MIN_ANABOLIC_HORMONE);
        this.catabolicHormone = Math.min(catabolicHormone, Params.MAX_CATABOLIC_HORMONE);
        this.catabolicHormone = Math.max(catabolicHormone, Params.MIN_CATABOLIC_HORMONE);
    }

    public double getAnabolicHormone() {
        return this.anabolicHormone;
    }
    public double getCatabolicHormone() {
        return this.catabolicHormone;
    }
    public void setAnabolicHormone(double anabolicHormone) {
        this.anabolicHormone = anabolicHormone;
    }
    public void setCatabolicHormone(double catabolicHormone) {
        this.catabolicHormone = catabolicHormone;
    }

    public double getFiberSize() {
        return this.fiberSize;
    }

    public void developMuscle() {
        this.fiberSize -= 0.20 * Math.log10(catabolicHormone);
        // anabolic hormones add mass to the fibers
        this.fiberSize += 0.20 * Math.min(Math.log10(anabolicHormone), 1.05 * Math.log10(catabolicHormone));
        regulateMuscleFibers();
    }

}
