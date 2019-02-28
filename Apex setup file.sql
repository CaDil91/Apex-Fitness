#drop database apex;
create database apex;
use apex;

create table customer(
	email varchar(50),
    f_name varchar(20),
    l_name varchar(20),
    age int,
    pw varchar(256), #security!
    reminder boolean,
    goal varchar(40),
    weight int,
    height int,
    primary key (email)
);

create table equipment(
    eq_name varchar(50),
    eq_description varchar(250),
    primary key (eq_name)
);

create table customerEquipment(
	email varchar(50),
    eq_name varchar(50),
    primary key (email, eq_name),
    foreign key (email) references customer(email),
    foreign key (eq_name) references equipment(eq_name)
);

create table exercises(
    ex_name varchar(50),
    ex_description varchar(50),
    body_part varchar(2),
    eq_name varchar(50),
    goal varchar(4),
    primary key (ex_name),
    foreign key (eq_name) references equipment(eq_name)
);

create table workouts(
	wk_id varchar(36), #uuid
    wk_name varchar(50),
    email varchar(50),
    createdDate date,
    completed boolean, #True = workout history, false = workout planner. Create seperate tables? Ok for now
    primary key (wk_id),
    foreign key (email) references customer(email)
);

create table workoutExercises(
	wk_id varchar(36), #uuid(), gotten from workouts table
    ex_name varchar(50),
    reps int, #cardio time?
    sets int, #cardio distance?
    weight int,
    primary key (wk_id, ex_name), #The workout id and exercise name combo is unique for a workout. Many exercise names for a single workout
    foreign key (wk_id) references workouts(wk_id), #The workout is created first, then this referenced to the workout
    foreign key (ex_name) references exercises(ex_name) #The workout is created first, then this referenced to the workout
);

insert into equipment values ('Dumbell', 'Dumbell.PNG');
insert into equipment values ('Barbell', 'Barbell.PNG');
insert into equipment values ('Treadmill', 'Treadmill.PNG');
insert into equipment values ('Body Weight', 'Body Weight.PNG');
insert into equipment values ('Dipping Bars', 'Dipping Bars.PNG');
insert into equipment values ('Jump Rope', 'Jump Rope.PNG');
insert into equipment values ('Pull-Up Bar', 'Pull-Up Bar.PNG');



#SH = Shoulders
#CH = Chest
#BA = Back
#BI = Biceps
#TR = Triceps
#LE = Legs
#AB = Abs
#CA = Cardio

#WL = Weight Loss
#MG = Muscle Gain

insert into customer values('caleb@gmail.com', 'Caleb', 'Dilworth', 25, 'asdf', false, 'MG', 200, 72);

insert into exercises values ('Dumbell Overhead Press', 'Dumbell Overhead Press.PNG', 'SH' ,'Dumbell', 'MG');
insert into exercises values ('Shoulder Side Lateral Raises', 'Shoulder Side Laterals.PNG', 'SH' ,'Dumbell', null);
insert into exercises values ('Shoulder Front Raises', 'Shoulder Front Raises.PNG', 'SH' ,'Dumbell', null);
insert into exercises values ('Dumbell Bench Press', 'Dumbell Bench Press.PNG', 'CH' ,'Dumbell', 'MG');
insert into exercises values ('Dumbell Flys', 'Dumbell Flys.PNG', 'CH' ,'Dumbell', null);
insert into exercises values ('One-Arm Dumbell Row', 'One-Arm Dumbell Row.PNG', 'BA' ,'Dumbell', null);
insert into exercises values ('Dumbell Renegade Rows', 'Dumbell Renegade Rows.PNG', 'BA' ,'Dumbell', null);
insert into exercises values ('Dumbell Bicep Curl', 'Dumbell Bicep Curl.PNG', 'BI' ,'Dumbell', 'MG');
insert into exercises values ('Hammer Curls', 'Hammer Curls.PNG', 'BI' ,'Dumbell', null);
insert into exercises values ('Overhead Tricep Press', 'Overhead Tricep Press.PNG', 'TR' ,'Dumbell', 'MG');
insert into exercises values ('Kickbacks', 'Kickbacks.PNG', 'TR' ,'Dumbell', null);
insert into exercises values ('Dumbell Side Bend', 'Side Bend.PNG', 'AB' ,'Dumbell', null);
insert into exercises values ('Dumbell Lunges', 'Dumbell Lunges.PNG', 'LE' ,'Dumbell', 'WL');
insert into exercises values ('Dumbell Squats', 'Dumbell Squats.PNG', 'LE' ,'Dumbell', null);

#Shoulders
insert into exercises values ('Barbell Overhead Press', 'Barbell Overhead Press.PNG', 'SH' ,'Barbell', 'MG');
insert into exercises values ('Barbell Upright Row', 'Barbell Upright Row.PNG', 'SH' ,'Barbell', null);
insert into exercises values ('Barbell Chest Press', 'Barbell Chest Press.PNG', 'CH' ,'Barbell', 'MG');
insert into exercises values ('Incline Barbell Chest Press', 'Incline Barbell Chest Press.PNG', 'CH' ,'Barbell', null);
insert into exercises values ('Deadlift', 'Deadlift.PNG', 'BA' ,'Barbell', 'MG');
insert into exercises values ('Barbell Bent Over Row', 'Barbell Bent Over Row.PNG', 'BA' ,'Barbell', null);
insert into exercises values ('Barbell Bicep Curl', 'Barbell Bicep Curl.PNG', 'BI' ,'Barbell', 'MG');
insert into exercises values ('Skullcrushers', 'Skullcrushers.PNG', 'TR' ,'Barbell', 'MG');
insert into exercises values ('Close Grip Bench Press', 'Close Grip Bench Press.PNG', 'TR' ,'Barbell', null);
insert into exercises values ('Barbell Ab Rollout', 'Barbell Ab Rollout.PNG', 'AB' ,'Barbell', 'MG');
insert into exercises values ('Landmines', 'Landmines.PNG', 'AB' ,'Barbell', 'WL');
insert into exercises values ('Squats', 'Squats.PNG', 'LE' ,'Barbell', 'MG');
insert into exercises values ('Barbell Lunges', 'Barbell Lunges.PNG', 'LE' ,'Barbell', 'WL');
insert into exercises values ('Pike Push-Ups', 'Pike Push-Ups.PNG', 'SH' ,'Body Weight', null);
insert into exercises values ('Push Ups', 'Push Ups.PNG', 'CH' ,'Body Weight', 'MG');
insert into exercises values ('Dips', 'Dips.PNG', 'TR' ,'Body Weight', 'MG');
insert into exercises values ('Diamond Push Ups', 'Diamond Push Ups.PNG', 'TR' ,'Body Weight', null);
insert into exercises values ('Crunches', 'Crunches.PNG', 'AB' ,'Body Weight', null);
insert into exercises values ('Lunges', 'Lunges.PNG', 'LE' ,'Body Weight', 'WL');

#Back
insert into exercises values ('Pull-ups', 'Pull-ups.PNG', 'BA' ,'Pull-up Bar', 'MG');
insert into exercises values ('Chin-ups', 'Chin-ups.PNG', 'BI' ,'Pull-up Bar', null);
insert into exercises values ('Hanging Leg Raises', 'Hanging Leg Raises.PNG', 'AB' ,'Pull-up Bar', null);
