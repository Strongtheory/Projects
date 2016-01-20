#include "maincontroller.h"

#include <QQmlComponent>
#include <QQmlContext>
#include <QFile>
#include <QDir>

MainController::MainController(QObject *parent) :
    QObject(parent),
    mainWindow(new MainWindow(NULL, this)),
    newGame(new NewGame(NULL, this)),
    settings(new Settings(NULL, this)),
    navigation(new Navigation(NULL, this)),
    planetNavigation(new PlanetNavigation(NULL, this)),
    marketplace(new Marketplace(NULL, this)),
    shipyard(new Shipyard(NULL, this)),
    fuelStation(new FuelStation(NULL, this))
{
    engine = new QQmlApplicationEngine(this);
    QQmlComponent component(engine, QUrl(QStringLiteral("qrc:///main.qml")));
    rootObject = component.create();

    mainWindow->setRootObject(rootObject);
    newGame->setRootObject(rootObject);
    settings->setRootObject(rootObject);
    navigation->setRootObject(rootObject);
    planetNavigation->setRootObject(rootObject);
    marketplace->setRootObject(rootObject);
    shipyard->setRootObject(rootObject);
    fuelStation->setRootObject(rootObject);

    engine->rootContext()->setContextProperty("mainController", this);
    engine->rootContext()->setContextProperty("mainWindow", mainWindow);
    engine->rootContext()->setContextProperty("newGame", newGame);
    engine->rootContext()->setContextProperty("settings", settings);
    engine->rootContext()->setContextProperty("navigation", navigation);
    engine->rootContext()->setContextProperty("planetNavigation", planetNavigation);
    engine->rootContext()->setContextProperty("marketplace", marketplace);
    engine->rootContext()->setContextProperty("shipyard", shipyard);
    engine->rootContext()->setContextProperty("fuelStation", fuelStation);

    showHomeScreen();
}

void MainController::showHomeScreen() {
    mainWindow->showHomeScreen();
}

void MainController::startNewGame() {
    newGame->showNewGamePage();
}

void MainController::showSettings() {
    settings->showSettingsPage();
}

void MainController::showNavigationPage() {
    navigation->showNavigationPage();
}

void MainController::showPlanetNavigationPage() {
    planetNavigation->showPlanetNavigationPage();
}

void MainController::showMarketplace() {
    marketplace->showMarketplace();
}

void MainController::showShipyard() {
    shipyard->showShipyard();
}

void MainController::showFuelStation() {
    fuelStation->showFuelStation();
}

void MainController::produceWaresForAllPlanets() {
    QList<SolarSystem> solarSystems = Galaxy::getInstance().getSolarSystems();
    for (int i = 0; i < solarSystems.size(); i++) {
        SolarSystem solarSystem = solarSystems.at(i);
        if (!(qrand() % 5)) {
            continue;
        }

        QList<Planet> planets = solarSystem.getPlanets().values();
        for (int j = 0; j < planets.size(); j++) {
            Planet planet = planets.at(j);
            planet.replenishWares();
        }
    }
}

void MainController::saveData() {
#if defined(Q_OS_ANDROID)
    QFile file(QDir::homePath() + QStringLiteral("/file.dat"));
#else
    QFile file(QDir::currentPath() + QStringLiteral("/file.dat"));
#endif
    file.open(QIODevice::WriteOnly);

    QDataStream out(&file);
    out << Galaxy::getInstance();
    out << Player::getInstance();
    file.close();
}

void MainController::loadData() {
    if (!isDataLoadable()) {
        return;
    }
#if defined(Q_OS_ANDROID)
    QFile file(QDir::homePath() + QStringLiteral("/file.dat"));
#else
    QFile file(QDir::currentPath() + QStringLiteral("/file.dat"));
#endif
    file.open(QIODevice::ReadOnly);

    QDataStream in(&file);
    Galaxy galaxy = Galaxy::getInstance();
    Player player = Player::getInstance();
    in >> galaxy;
    in >> player;
    file.close();
}

bool MainController::isDataLoadable() {
#if defined(Q_OS_ANDROID)
    QFile file(QDir::homePath() + QStringLiteral("/file.dat"));
#else
    QFile file(QDir::currentPath() + QStringLiteral("/file.dat"));
#endif

    return file.exists();
}

MainController::~MainController() {
    delete rootObject;
    delete engine;
    delete mainWindow;
    delete newGame;
}
