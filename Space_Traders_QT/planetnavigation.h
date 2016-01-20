#ifndef PLANETNAVIGATION_H
#define PLANETNAVIGATION_H

#include <QObject>
#include "galaxy.h"
#include "player.h"

class PlanetNavigation : public QObject
{
    Q_OBJECT
public:
    explicit PlanetNavigation(QObject *rootObject = 0, QObject *parent = 0);

    void setRootObject(QObject* rootObject);
    void showPlanetNavigationPage();

    Q_INVOKABLE void travelToPlanet(QString planet);

private:
    QObject *rootObject;

    Galaxy galaxy;
    Player player;

};

#endif // PLANETNAVIGATION_H
