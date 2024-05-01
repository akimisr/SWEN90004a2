public class Main {
    public static void main(String[] args) {
        int ticks = 1;
        boolean lift = true;
        int days_between_workouts = 5;
        int intensity = 95;
        double hours_of_sleep = 8;
        int percentage_slow_twitch_fibers = 50;
        double[] average = new double[2];

        MuscleDevelopmentModel test_model = new MuscleDevelopmentModel(25, 25, 75, percentage_slow_twitch_fibers);
        
        for (ticks = 0; ticks < 30; ticks++) { // 时间遍历
            average = test_model.getAverageHormone();
            double mass = test_model.getMuscleMass();
            System.out.println(ticks);
            System.out.println(average[0]);
            System.out.println(mass);
            test_model.simulate(ticks, lift, days_between_workouts, intensity, hours_of_sleep);
        }
    }

    
    
    
}

    
