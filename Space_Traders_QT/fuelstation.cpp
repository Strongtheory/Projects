#include "fuelstation.h"
#include "player.h"
#include <QVariant>

FuelStation::FuelStation(QObject* rootObject, QObject *parent) :
    QObject(parent),
    rootObject(rootObject)
{
}

void FuelStation::setRootObject(QObject *rootObject) {
    this->rootObject = rootObject;
}

void FuelStation::showFuelStation() {
    Q_ASSERT(rootObject);
    rootObject->findChild<QObject*>("pageLoader")->setProperty("source", QStringLiteral("qrc:///fuelStation.qml"));

    Player player = Player::getInstance();
    Ship ship = player.getShip();

    rootObject->findChild<QObject*>("fuelStationScreen")->setProperty("fuelCapacity", ship.getMaxFuel());
    rootObject->findChild<QObject*>("fuelStationScreen")->setProperty("currentFuel", ship.getFuel());
}

void FuelStation::buyFuel() {
    Player player = Player::getInstance();
    player.getShip().setFuel(player.getShip().getFuel() + 5);
    rootObject->findChild<QObject*>("fuelStationScreen")->setProperty("currentFuel", player.getShip().getFuel());
}

void FuelStation::sellFuel() {
    Player player = Player::getInstance();
    player.getShip().setFuel(player.getShip().getFuel() - 5);
    rootObject->findChild<QObject*>("fuelStationScreen")->setProperty("currentFuel", player.getShip().getFuel());
}
