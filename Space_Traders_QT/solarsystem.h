#ifndef SOLARSYSTEM_H
#define SOLARSYSTEM_H

#include <QObject>
#include <QColor>
#include <QDataStream>
#include <QExplicitlySharedDataPointer>
#include <QStringList>

#include "planet.h"

class SolarSystemData;

class SolarSystem : public QObject
{
public:
    SolarSystem(QString name = QStringLiteral(""));
    SolarSystem(QString name, int x, int y, QMap<QString, Planet> planets);
    SolarSystem(const SolarSystem &);
    SolarSystem &operator=(const SolarSystem &);
    ~SolarSystem();

    QString getName() const;
    Planet getPlanet(QString planetName) const;
    QMap<QString, Planet> getPlanets() const;
    int getX() const;
    int getY() const;
    QColor getColor() const;

private:
    QExplicitlySharedDataPointer<SolarSystemData> data;
};

Q_DECLARE_TYPEINFO(SolarSystem, Q_MOVABLE_TYPE);

bool operator==(const SolarSystem solarSystem1, const SolarSystem solarSystem2);

QDataStream& operator<<(QDataStream& stream, const SolarSystem solarSystem);
QDataStream& operator>>(QDataStream& stream, SolarSystem& solarSystem);

#endif // SOLARSYSTEM_H
