package menu;



public class App 
{
    public static void main( String[] args ) {
        MenuService menuService = new MenuService("resources/car1.json", "resources/car2.json", "resources/car3.json", "resources/car4.json");
        menuService.mainMenu();
    }
}
