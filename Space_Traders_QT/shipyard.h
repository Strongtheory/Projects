#ifndef SHIPYARD_H
#define SHIPYARD_H

#include <QObject>

class Shipyard : public QObject
{
    Q_OBJECT
public:
    explicit Shipyard(QObject *rootObject = 0, QObject *parent = 0);

    void setRootObject(QObject *rootObject);
    void showShipyard();

    Q_INVOKABLE void instantiateShipTab();
    Q_INVOKABLE void instantiateGadgetsTab();
    Q_INVOKABLE void instantiateShieldsTab();
    Q_INVOKABLE void instantiateWeaponsTab();

    Q_INVOKABLE void buyShip(int index);
    Q_INVOKABLE void buyGadget(int index);
    Q_INVOKABLE void sellGadget(int index);
    Q_INVOKABLE void buyShield(int index);
    Q_INVOKABLE void sellShield(int index);
    Q_INVOKABLE void buyWeapon(int index);
    Q_INVOKABLE void sellWeapon(int index);

private:
    QObject* rootObject;

};

#endif // SHIPYARD_H
