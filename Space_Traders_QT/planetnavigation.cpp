#include "planetnavigation.h"
#include <QVariant>

PlanetNavigation::PlanetNavigation(QObject *rootObject, QObject *parent) :
    QObject(parent),
    rootObject(rootObject),
    galaxy(Galaxy::getInstance()),
    player(Player::getInstance())
{
}

void PlanetNavigation::setRootObject(QObject *rootObject) {
    this->rootObject = rootObject;
}

void PlanetNavigation::showPlanetNavigationPage() {
    Q_ASSERT(rootObject);
    rootObject->findChild<QObject*>("pageLoader")->setProperty("source", QStringLiteral("qrc:///planetNavigation.qml"));

    QObject* navigationRectangle = rootObject->findChild<QObject*>("planetNavigationScreen");

    QList<Planet> planets = player.getCurrentSystem().getPlanets().values();
    Planet currentPlanet = player.getCurrentPlanet();

    for (int i = 0; i < planets.size(); i++) {
        const Planet planet = planets.at(i);
        QMetaObject::invokeMethod(navigationRectangle, "createPlanetButtons",
                                  Q_ARG(QVariant, planet.getName()),
                                  Q_ARG(QVariant, planet.getTechLevelString()),
                                  Q_ARG(QVariant, planet.getResourceTypeString()),
                                  Q_ARG(QVariant, planet.getColor()),
                                  Q_ARG(QVariant, planet.getRadius()),
                                  Q_ARG(QVariant, planet == currentPlanet));
    }
}

void PlanetNavigation::travelToPlanet(QString planet) {
    Player player = Player::getInstance();
    Planet destination = player.getCurrentSystem().getPlanet(planet);
    player.setCurrentPlanet(destination);

    QObject* navigationRectangle = rootObject->findChild<QObject*>("planetNavigationScreen");

    QMetaObject::invokeMethod(navigationRectangle, "setNewCurrentPlanet",
                              Q_ARG(QVariant, planet));
}
