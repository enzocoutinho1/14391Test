package com.example.meepmeeptesting;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.acmerobotics.roadrunner.geometry.Vector2d;
import com.noahbres.meepmeep.MeepMeep;
import com.noahbres.meepmeep.core.colorscheme.scheme.ColorSchemeBlueDark;
import com.noahbres.meepmeep.core.colorscheme.scheme.ColorSchemeRedDark;
import com.noahbres.meepmeep.core.colorscheme.scheme.ColorSchemeRedLight;
import com.noahbres.meepmeep.roadrunner.DefaultBotBuilder;
import com.noahbres.meepmeep.roadrunner.entity.RoadRunnerBotEntity;

public class MeepMeepTesting {
    static String zona = "1";
    static double y1 = 0;
    static double y2 = 0;
    public static void main(String[] args) {
        switch (zona) {
            case "1":
                y1 = -10;
                y2 = -60;
                break;
            case "2":
                y1 = -36;
                y2 = -36;
                break;
            case "3":
                y1 = -60;
                y2 = -10;
                break;
        }
        System.setProperty("sun.java2d.opengl", "true");
        MeepMeep meepMeep = new MeepMeep(650);

        // Declare our first bot
        RoadRunnerBotEntity myFirstBot = new DefaultBotBuilder(meepMeep)
                // We set this bot to be blue
                .setColorScheme(new ColorSchemeRedDark())
                .setConstraints(50, 50, Math.toRadians(180), Math.toRadians(180), 16.28)
                .followTrajectorySequence(drive ->
                        drive.trajectorySequenceBuilder(new Pose2d(-35, -60, Math.toRadians(90)))
                                .splineTo(new Vector2d(-27, -35), 0)
                                .splineTo(new Vector2d(-10.5, -35), 0)
                                .splineToConstantHeading(new Vector2d(-10.5, -23), 0)
                                .addDisplacementMarker(() -> {
                                    //abaixar e levantar servo aqui
                                })
                                .waitSeconds(1.5)
                                .strafeTo(new Vector2d(-10.5, y1))
                                .build()
                );
        // Declare our second bot
       /* RoadRunnerBotEntity mySecondBot = new DefaultBotBuilder(meepMeep)
                // We set this bot to be blue
                .setColorScheme(new ColorSchemeRedLight())
                .setConstraints(50, 50, Math.toRadians(180), Math.toRadians(180), 16.28)
                .followTrajectorySequence(drive ->
                        drive.trajectorySequenceBuilder(new Pose2d(35, -60, Math.toRadians(90)))
                                .splineTo(new Vector2d(30, -35), Math.toRadians(180))
                                .splineTo(new Vector2d(10.5, -35), Math.toRadians(180))
                                .splineToConstantHeading(new Vector2d(10.5, -23), Math.toRadians(180))
                                .addDisplacementMarker(() -> {
                                    //abaixar e levantar servo aqui
                                })
                                .waitSeconds(1.5)
                                .strafeTo(new Vector2d(10.8, y))
                                .build()
                );*/

       /* RoadRunnerBotEntity myThirdBot = new DefaultBotBuilder(meepMeep)
                // We set this bot to be blue
                .setColorScheme(new ColorSchemeBlueDark())
                .setConstraints(50, 50, Math.toRadians(180), Math.toRadians(180), 16.28)
                .followTrajectorySequence(drive ->
                        drive.trajectorySequenceBuilder(new Pose2d(35, 60, Math.toRadians(270)))
                                .splineTo(new Vector2d(30, 35), Math.toRadians(180))
                                .splineTo(new Vector2d(10.5, 35), Math.toRadians(180))
                                .splineToConstantHeading(new Vector2d(10.5, 23), Math.toRadians(180))
                                .addDisplacementMarker(() -> {
                                    //abaixar e levantar servo aqui
                                })
                                .waitSeconds(1.5)
                                .strafeTo(new Vector2d(10.8, -1* y))
                                .build()
                );*/

        RoadRunnerBotEntity myFourthBot = new DefaultBotBuilder(meepMeep)
                // We set this bot to be blue
                .setColorScheme(new ColorSchemeRedLight())
                .setConstraints(50, 50, Math.toRadians(180), Math.toRadians(180), 16.28)
                .followTrajectorySequence(drive ->
                        drive.trajectorySequenceBuilder(new Pose2d(35, -60, Math.toRadians(90)))
                                .splineTo(new Vector2d(30, -35), Math.toRadians(180))
                                .splineTo(new Vector2d(10.5, -35), Math.toRadians(135))
                                .addDisplacementMarker(() -> {
                                    //abaixar e levantar servo aqui
                                })
                                .waitSeconds(1.5)
                                .splineToConstantHeading(new Vector2d(14, y2), Math.toRadians(180))
                                .build()
                );
        meepMeep.setBackground(MeepMeep.Background.FIELD_POWERPLAY_KAI_DARK)
                .setDarkMode(true)
                .setBackgroundAlpha(0.95f)

                // Add both of our declared bot entities
                .addEntity(myFirstBot)
               // .addEntity(mySecondBot)
                // .addEntity(myThirdBot)]
                .addEntity(myFourthBot)
                .start();
    }
}