
public class MuscleDevelopmentModel {
    private int width;
    private int height;
    private int diffusionRate;
    private MuscleFiber[][] patches;

    MuscleDevelopmentModel(int width, int height, int diffuseRate, int percentage_slow_twitch_fibers) {
        this.width = width;
        this.height = height;
        this.diffusionRate = diffuseRate;
        this.patches = new MuscleFiber[this.width][this.height];
        for (int i = 0; i < this.width; i++) {
            for (int j = 0; j < this.height; j++) {
                // 创建 MuscleFiber 对象，并将其放入数组
                this.patches[i][j] = new MuscleFiber(percentage_slow_twitch_fibers);
            }
        }
    }

    private void performActivities(int ticks, boolean lift, int days_between_workouts, int intensity, double hours_of_sleep) {
        for (int i = 0; i < this.width; i++) { // 行遍历
            // perform activities (daily activity, lift, sleep)
            for (int j = 0; j < this.height; j++) { // 列遍历
                this.patches[i][j].performActivities(ticks, lift, days_between_workouts, intensity, hours_of_sleep);
            }
        }
    }

    private void diffuse() {
        double[][] nextAnabolicHormone = new double[this.width][this.height];
        double[][] nextCatabolicHormone = new double[this.width][this.height];
        // get new values for two hormones
        for (int i = 0; i < this.width; i++) { // 行遍历
            for (int j = 0; j < this.height; j++) { // 列遍历
                double[] newValue = getNewValue(i, j);
                nextAnabolicHormone[i][j] = newValue[0];
                nextCatabolicHormone[i][j] = newValue[1];
            }
        }
        // set new values for two hormones and regulate them
        for (int i = 0; i < this.width; i++) { // 行遍历
            for (int j = 0; j < this.height; j++) { // 列遍历
                this.patches[i][j].setAnabolicHormone(nextAnabolicHormone[i][j]);
                this.patches[i][j].setCatabolicHormone(nextCatabolicHormone[i][j]);
                this.patches[i][j].regulateHormones();
                // System.out.print(this.patches[i][j].getAnabolicHormone() + " ");
            }
        }
    }

    private double[] getNewValue(int x, int y){
        double[] values = new double[2];
        double sumAnabolicHormone = 0.0;
        double sumCatabolicHormone = 0.0;
        int count = 0;
        for (int i = -1; i <= 1; i++) {
            for (int j = -1; j <= 1; j++) {
                int neighborX = (x + i + this.width) % this.width;
                int neighborY = (y + j + this.height) % this.height;
                if (neighborX==x && neighborY==y) {
                    continue;
                }
                sumAnabolicHormone += this.patches[neighborX][neighborY].getAnabolicHormone();
                sumCatabolicHormone += this.patches[neighborX][neighborY].getCatabolicHormone();
                count++;
            }
        }
        double averageAnabolicHormone = sumAnabolicHormone / count;
        double averageCatabolicHormone = sumCatabolicHormone / count;

        values[0] = (1 - this.diffusionRate) * this.patches[x][y].getAnabolicHormone() + this.diffusionRate * averageAnabolicHormone;
        values[1] = (1 - this.diffusionRate) * this.patches[x][y].getCatabolicHormone() + this.diffusionRate * averageCatabolicHormone;
        return values;
    }

    private void developMuscle() {
        for (int i = 0; i < this.width; i++) { // 行遍历
            for (int j = 0; j < this.height; j++) { // 列遍历
                this.patches[i][j].developMuscle();
            }
        }
    }

    public double[] getAverageHormone() {
        double[] values = new double[2];
        double sumAnabolicHormone = 0.0;
        double sumCatabolicHormone = 0.0;
        int count = 0;
        for (int i = 0; i < this.width; i++) { // 行遍历
            for (int j = 0; j < this.height; j++) { // 列遍历
                sumAnabolicHormone += this.patches[i][j].getAnabolicHormone();
                sumCatabolicHormone += this.patches[i][j].getCatabolicHormone();
                count++;
            }
        }
        values[0] = sumAnabolicHormone / count;
        values[1] = sumCatabolicHormone / count;

        return values;
    }

    public double getMuscleMass() {
        double sumMass = 0.0;
        for (int i = 0; i < this.width; i++) { // 行遍历
            for (int j = 0; j < this.height; j++) { // 列遍历
                sumMass += this.patches[i][j].getFiberSize();
            }
        }
        return sumMass;
    }

    public void simulate(int ticks, boolean lift, int days_between_workouts, int intensity, double hours_of_sleep) {
        performActivities(ticks, lift, days_between_workouts, intensity, hours_of_sleep);

        // System.out.println(getMuscleMass());
        diffuse();

        developMuscle();
        // System.out.println(getMuscleMass());

    }

}
