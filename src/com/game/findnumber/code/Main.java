package com.game.findnumber.code;

public class Main {
    public static void main(String[] args) {
        Time time = new Time(0, 0);
        PanelGame panelGame = new PanelGame();
        panelGame.setVisible(true);
        boolean check = true;
        while (true) {
            try {
                for (int i = 0; i < 20; i++) {
                    Thread.sleep(45);
                    if (panelGame.isQuit()) {
                        System.exit(0);
                    }
                    if (panelGame.isPause()) {
                        check = true;
                        continue;
                    } else if (check && panelGame.isStart() && panelGame.isCreateNumbers()) {
                        panelGame.showNumberEffect();
                        time.setTime(0, 0);
                        check = false;
                    }
                    if (panelGame.isComplete()) {
                        break;
                    }
                }
                if (!panelGame.isComplete() && (panelGame.isRemuse() || panelGame.isStart())) {
                    panelGame.setLabelTime(time.toString());
                    time.nextSecond();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
