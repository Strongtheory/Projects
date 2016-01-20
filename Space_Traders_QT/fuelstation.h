#ifndef FUELSTATION_H
#define FUELSTATION_H

#include <QObject>

class FuelStation : public QObject
{
    Q_OBJECT
public:
    explicit FuelStation(QObject *rootObject, QObject *parent = 0);

    void setRootObject(QObject *rootObject);
    void showFuelStation();

    Q_INVOKABLE void buyFuel();
    Q_INVOKABLE void sellFuel();

private:
    QObject* rootObject;

};

#endif // FUELSTATION_H
