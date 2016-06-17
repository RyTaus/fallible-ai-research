public class ActionMenu {
    public enum Option {
        Attack,
        Wait;
        
        public AnimationType toAnimType() {
            switch (this) {
            case Attack:
                return AnimationType.ATTACK;
            }
            System.out.println("toAnimType asked for non anim option");
            return AnimationType.WAIT;
        }
    }
    
    public enum State {
        Primary,
        SelectTarget
    }
    
    Unit[] canAttack;
    
    Menu<Option> menu;
    Menu<Unit> targets;
    Unit uoi;
    GameBoard gb;
    State state = State.Primary;
    
    public ActionMenu(GameBoard b, Unit troop, Unit[] ca) {
        canAttack = ca;
        if (ca.length > 0) {
            menu = new Menu<Option>(new Option[] {Option.Attack, Option.Wait});
        } else {
            menu = new Menu<Option>(new Option[] {Option.Wait});
        }
        uoi = troop;
        gb = b;
    }
    
    public void move(Direction dir) {
        if (state == State.Primary) {
            menu.option(dir);
//            System.out.println(menu);
        } else {
            targets.option(dir);
//            System.out.println(targets);
        }
    }
    
    public GameBoard.State press() {
        if (state == State.Primary) {
            if (menu.select() == Option.Attack) {
                targets = new Menu<Unit>(canAttack);
                state = State.SelectTarget;
                return GameBoard.State.SELECT_ACTION;
            } else {
                return GameBoard.State.SELECT_UNIT;
            }
        } else {
            //TODO
            return GameBoard.State.BATTLE;
        }
    }
    
    public GameBoard.State back() {
        if (state == State.Primary) {
            return GameBoard.State.SELECT_DESTINATION;
        } else {
            state = State.Primary;
            return GameBoard.State.SELECT_ACTION;
        }
    }
    
    public String[] toStrings() {
        if (state == State.Primary) {
            String[] toReturn = new String[menu.options.size()];
            for (int i = 0; i < toReturn.length; i ++) {
                toReturn[i] = menu.options.get(i).toString();
            }
            return toReturn;
        } else {
            return new String[] {targets.select().job.name(), 
                    "Lv: " + targets.select().lvl, "HP: " + targets.select().currHP + "/" + targets.select().maxHP,
                    "Exp Dam: " + gb.expectedDamage(uoi, uoi.position, targets.select()), 
                    "Exp Rec: " + gb.expectedRecDamage(uoi, uoi.position, targets.select())};
        }
    }

}