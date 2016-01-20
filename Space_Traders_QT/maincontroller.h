#ifndef MAINCONTROLLER_H
#define MAINCONTROLLER_H

#include <QObject>
#include <QQmlApplicationEngine>

#include "newgame.h"
#include "mainwindow.h"
#include "settings.h"
#include "navigation.h"
#include "planetnavigation.h"
#include "marketplace.h"
#include "shipyard.h"
#include "fuelstation.h"

#include "galaxy.h"
#include "player.h"

class MainController : public QObject
{
    Q_OBJECT
public:
    explicit MainController(QObject *parent = 0);
    ~MainController();

    Q_INVOKABLE void showHomeScreen();
    Q_INVOKABLE void startNewGame();
    Q_INVOKABLE void showSettings();
    Q_INVOKABLE void showNavigationPage();
    Q_INVOKABLE void showPlanetNavigationPage();
    Q_INVOKABLE void showMarketplace();
    Q_INVOKABLE void showShipyard();
    Q_INVOKABLE void showFuelStation();

    void produceWaresForAllPlanets();

    Q_INVOKABLE void saveData();
    Q_INVOKABLE void loadData();
    Q_INVOKABLE bool isDataLoadable();

private:
    QQmlApplicationEngine* engine;
    QObject* rootObject;

    MainWindow* mainWindow;
    NewGame* newGame;
    Settings* settings;
    Navigation* navigation;
    PlanetNavigation* planetNavigation;
    Marketplace* marketplace;
    Shipyard* shipyard;
    FuelStation* fuelStation;

    Galaxy galaxy = Galaxy::getInstance();
    Player player = Player::getInstance();

};

#endif // MAINCONTROLLER_H
