public class Unit {

    public Coord position;
    public int lvl;
    public int maxHP;
    public int str;
    public int def;
    public int speed;
    public Class job;
    public int currHP;
    public boolean isDone;
    public TeamType team;

    public Unit(Coord pos, int level, int hp, int strength, int defense, int spd, Class type, TeamType side) {
        position = pos;
        lvl = level;
        maxHP = hp;
        currHP = hp;
        str = strength;
        def = defense;
        speed = spd;
        job = type;
        team = side;
        isDone = false;
    }

    public void Move(Coord coord) {
        // if (isValidMove(coord)) {
            position.moveTo(coord);
        // }

    }

    public int expectedDamage(Unit enemy) {
        if (str - enemy.def <= 0) {
            return 0;
        }
        if (canAttackTwice(enemy)) {
            return (str - enemy.def)*2;               //Shows how much damage will get through defense
        }
        return str - enemy.def;
    }

    public boolean canAttackTwice(Unit enemy) {
        return speed/enemy.speed >= 2;
    }

    public void Battle(Unit enemy) {
        //if (canAttack(coord)) {
            enemy.currHP =- expectedDamage(enemy);
            currHP =- enemy.expectedDamage(this);
        //}

        // if (canAttack(coord)) {

        // }

    }

    public void setTeam(TeamType whichTeam) {
        team = whichTeam;
    }

    // public Wait() {

    // }

    // public boolean isValidMove(int[] coord) {
    //     int distanceAway = (coord[0] + coord[1] - 1) - (position[0] + position[1]);

    //     if (job.movement >= distanceAway) {
    //         return true;
    //     }
    //     return false;
    // }

    // public canAttack(int[] coord) {

    // }

}
