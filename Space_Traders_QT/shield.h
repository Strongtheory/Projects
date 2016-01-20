#ifndef SHIELD_H
#define SHIELD_H

#include <QObject>

#include <QSharedDataPointer>

class ShieldData;

class Shield : public QObject
{
public:

    enum ShieldType {
        Unknown = -1,
        CivilianEnergy,
        MilitiaEnergy,
        MilitaryEnergy,
        CivilianReflective,
        MilitiaReflective,
        MilitaryReflective,

        SIZE_SHIELD_TYPE
    };

    Shield(ShieldType type = Unknown);
    Shield(const Shield &);
    Shield &operator=(const Shield &);
    ~Shield();

    ShieldType getType() const;
    QString getName() const;
    int getPrice() const;
    int getStrength() const;
    bool isReflective() const;

private:
    QSharedDataPointer<ShieldData> data;
};

bool operator==(const Shield& shield1, const Shield& shield2);
uint qHash(const Shield& shield);

QDataStream& operator<<(QDataStream& stream, const Shield shield);
QDataStream& operator>>(QDataStream& stream, Shield& shield);

QDataStream& operator<<(QDataStream& stream, const Shield::ShieldType shieldType);
QDataStream& operator>>(QDataStream& stream, Shield::ShieldType& shieldType);

#endif // SHIELD_H
